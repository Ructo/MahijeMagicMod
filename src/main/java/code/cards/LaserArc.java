package code.cards;

import code.cards.cardvars.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import static code.ModFile.makeID;

public class LaserArc extends AbstractEasyCard {
    public static final String ID = makeID("LaserArc");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public LaserArc() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 2;
        tags.add(CustomTags.LASERS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dexterityBonus = getDexterityBonus(p);
        int totalDamage = baseDamage + dexterityBonus; // Calculate total damage with dexterity

        // Perform damage action on a single target
        addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));

        // Exhaust a card
        addToBot(new ExhaustAction(p, p, 1, false, false, false));

        updateDescription(dexterityBonus);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new LaserArc();
    }

    private void updateDescription(int dexterityBonus) {
        this.rawDescription = cardStrings.DESCRIPTION
                .replace("!D!", String.valueOf(baseDamage + dexterityBonus));

        initializeDescription();
    }

    private int getDexterityBonus(AbstractPlayer player) {
        AbstractPower dexterityPower = player.getPower(DexterityPower.POWER_ID);
        return dexterityPower != null ? dexterityPower.amount : 0;
    }
}
