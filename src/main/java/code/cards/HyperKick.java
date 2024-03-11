package code.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.ModFile.makeID;

public class HyperKick extends AbstractEasyCard {
    public final static String ID = makeID("HyperKick");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HyperKick() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot((AbstractGameAction)new ExhaustAction(1, false));

    }


    public void upp() {
        upgradeDamage(2);
        initializeDescription(); // Update description when upgraded
    }

    @Override
    public void initializeDescription() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        super.initializeDescription();
    }
}