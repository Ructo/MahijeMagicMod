package code.cards.Depreciated;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.powers.ScalingVigorPower;
import code.powers.ScalingBlockPower;
import code.cards.AbstractEasyCard;

import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;
@NoPools
@NoCompendium

public class Adderall extends AbstractEasyCard {
    public static final String ID = makeID("Adderall");

    public Adderall() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ScalingVigorPower(p, this.magicNumber));
        applyToSelf(new ScalingBlockPower(p, this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1); // Increase the scaling effect for both Vigor and Block
    }
}
