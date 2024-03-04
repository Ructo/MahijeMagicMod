package code.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static code.ModFile.makeID;

public class Ping extends AbstractEasyCard {
    public final static String ID = makeID("Ping");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Ping() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 1; // Used for the number of cards to draw and Vigor to apply when upgraded
        this.magicNumber = this.baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int enemyCount = (int) AbstractDungeon.getMonsters().monsters.stream().filter(monster -> !monster.isDeadOrEscaped()).count();

        // Draw 1 card per enemy
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, enemyCount));

        // If upgraded, also apply 3 Vigor per enemy
        if (this.upgraded) {
            for (int i = 0; i < enemyCount; i++) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber * 3), this.magicNumber * 3));
            }
        }
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            ExhaustiveVariable.upgrade(this, 1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
