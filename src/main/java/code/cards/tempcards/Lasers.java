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
            AbstractMonster randomTarget = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToBot(new DamageAction(randomTarget, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo); // Apply all standard damage calculations, including Strength.

        int dexterityBonus = getDexterityBonus(AbstractDungeon.player);
        int strengthAmount = getStrengthAmount(AbstractDungeon.player);

        // Adjust damage based on Dexterity, then negate the Strength effect.
        this.damage += dexterityBonus;
        this.damage -= strengthAmount;

        // Ensure damage does not drop below baseDamage.
        this.damage = Math.max(this.damage, baseDamage);

        updateDescription(dexterityBonus);
    }

    private void updateDescription(int dexterityBonus) {
        // Assumes the cardStrings.DESCRIPTION already contains placeholders for dynamic values
        this.rawDescription = cardStrings.DESCRIPTION.replace("!D!", String.valueOf(baseDamage + dexterityBonus));
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Lasers();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // Increases the number of hits
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    private int getDexterityBonus(AbstractPlayer player) {
        AbstractPower dexterityPower = player.getPower(DexterityPower.POWER_ID);
        return dexterityPower != null ? dexterityPower.amount : 0;
    }

    private int getStrengthAmount(AbstractPlayer player) {
        AbstractPower strengthPower = player.getPower("Strength");
        return strengthPower != null ? strengthPower.amount : 0;
    }
}
