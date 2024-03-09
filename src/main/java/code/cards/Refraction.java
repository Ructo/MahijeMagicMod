package code.cards;

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

public class Refraction extends AbstractEasyCard {
    public static final String ID = makeID("Refraction");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Refraction() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 2;
        baseMagicNumber = 0; // This will be dynamically set based on Dexterity and Lasers played
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int lasersPlayed = countLasersPlayed();

        if (lasersPlayed > 0) {
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.miscRng);
            int dexterityBonus = getDexterityBonus(p);

            // Update description to reflect current state
            updateDescription(lasersPlayed, dexterityBonus);

            for (int i = 0; i < lasersPlayed; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(randomMonster, new DamageInfo(p, baseDamage + dexterityBonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Refraction();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    private void updateDescription(int lasersPlayed, int dexterityBonus) {
        int totalDamage = (baseDamage + dexterityBonus) * lasersPlayed;
        this.rawDescription = cardStrings.DESCRIPTION
                .replace("!D!", String.valueOf(baseDamage + dexterityBonus))
                .replace("!M!", String.valueOf(totalDamage))
                .replace("!L!", String.valueOf(lasersPlayed));
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        int lasersPlayed = countLasersPlayed();
        int dexterityBonus = getDexterityBonus(AbstractDungeon.player);
        updateDescription(lasersPlayed, dexterityBonus);
        super.applyPowers();
    }

    private int countLasersPlayed() {
        return (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream()
                .filter(c -> c.cardID.equals(makeID("Lasers")))
                .count();
    }

    private int getDexterityBonus(AbstractPlayer player) {
        AbstractPower dexterityPower = player.getPower(DexterityPower.POWER_ID);
        return dexterityPower != null ? dexterityPower.amount : 0;
    }
}
