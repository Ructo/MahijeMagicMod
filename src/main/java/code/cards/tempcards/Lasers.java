package code.cards.tempcards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import code.cards.AbstractEasyCard;
import code.cards.cardvars.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.ModFile.makeID;

@NoPools
@NoCompendium
public class Lasers extends AbstractEasyCard {
    public static final String ID = makeID("Lasers");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Lasers() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        baseDamage = 2;
        baseMagicNumber = magicNumber = 2;
        this.exhaust = true;
        tags.add(CustomTags.LASERS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractMonster randomTarget = AbstractDungeon.getMonsters().getRandomMonster(null,true, AbstractDungeon.cardRandomRng);
            addToBot(new DamageAction(randomTarget, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void applyPowers() {
        int originalBaseDamage = this.baseDamage;
        this.baseDamage += getDexterityBonus(AbstractDungeon.player) - getStrengthAmount(AbstractDungeon.player);

        super.applyPowers();

        this.baseDamage = originalBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalBaseDamage = this.baseDamage;
        this.baseDamage += getDexterityBonus(AbstractDungeon.player) - getStrengthAmount(AbstractDungeon.player);

        super.calculateCardDamage(mo);

        this.baseDamage = originalBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    private int getDexterityBonus(AbstractPlayer player) {
        AbstractPower dexterityPower = player.getPower(DexterityPower.POWER_ID);
        return dexterityPower != null ? dexterityPower.amount : 0;
    }

    private int getStrengthAmount(AbstractPlayer player) {
        AbstractPower strengthPower = player.getPower(StrengthPower.POWER_ID);
        return strengthPower != null ? strengthPower.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lasers();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // Increase the number of hits
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
