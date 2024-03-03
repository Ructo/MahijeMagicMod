package code.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static code.ModFile.makeID;

public class HyperVigilance extends AbstractEasyCard {
    public static final String ID = makeID("HyperVigilance");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public HyperVigilance() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1; // Used for the VigilantPower
        baseSecondMagic = secondMagic = 1; // Used for the Artifact effect
        baseBlock = 1;
        this.exhaust = true;
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck() ;
        this.addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, this.secondMagic), this.secondMagic));
    }


    public void upp() {
        upgradeSecondMagic(1);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();

    }
}