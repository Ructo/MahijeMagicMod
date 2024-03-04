package code.cards;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;


public class TemperanceSpiteful extends AbstractSwappableCard {
    public static final String ID = makeID("TemperanceSpiteful");
    public TemperanceSpiteful() {
        this(new TemperancePatient(null));
    }
    public TemperanceSpiteful(AbstractSwappableCard linkedCard) {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        this.baseDamage = this.damage = 3;
        this.exhaust = true;

        if (linkedCard == null) {
            this.setLinkedCard(new TemperancePatient(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int numSkillsExhausted = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.SKILL && c != this) {
                numSkillsExhausted++;
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
            }
        }
        if (numSkillsExhausted > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, numSkillsExhausted), numSkillsExhausted));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, false));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.cardsToPreview.upgrade();
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
        return new TemperanceSpiteful(null);
    }
}
