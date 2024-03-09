package code.cards;

import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;

public class Pong extends AbstractSwappableCard {
    public final static String ID = makeID("Pong");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public Pong() {
        this(new Ping(null));
    }

    public Pong(AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY, TEAL_COLOR);
        this.magicNumber = this.baseMagicNumber = 1; // Used for the number of Vulnerable stacks to apply
        ExhaustiveVariable.setBaseValue(this, 2);
        if (linkedCard == null) {
            this.setLinkedCard(new Ping(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int enemyCount = (int) AbstractDungeon.getMonsters().monsters.stream().filter(monster -> !monster.isDeadOrEscaped()).count();

        // Draw 1 card per enemy
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, enemyCount));

        // Apply 1 Weak to each enemy
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber));
            }
        }
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.player != null && !AbstractDungeon.isScreenUp) {
            AbstractCard newCard = this.cardsToPreview.makeStatEquivalentCopy();
            AbstractDungeon.actionManager.addToBottom(new SwapCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Pong(null);
    }
}