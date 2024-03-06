package code.cards;

import code.actions.EasyXCostAction;
import code.cards.AbstractEasyCard;
import code.powers.ReadyWeaponsPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelfTop;
import static code.util.Wiz.atb;

public class HyperElectroBeam extends AbstractEasyCard {
    public static final String ID = makeID("HyperElectroBeam");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HyperElectroBeam() {
        super(ID, -1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        damage = baseDamage = 1;
        setMagic(0, +1);
        PurgeField.purge.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (p.hasPower(ReadyWeaponsPower.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(p, p, ReadyWeaponsPower.POWER_ID));}
            for (int i = 0; i < effect + params[0]; i++) {
                allDmg(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            }
            return true;
        }, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}