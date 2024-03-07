package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static code.ModFile.makeID;

public class ZeroPointOverdrive extends AbstractEasyCard {
    public final static String ID = makeID("ZeroPointOverdrive");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public ZeroPointOverdrive() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 15; // Initial base damage
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers(); // Make sure to recalculate damage based on current game state
        int damage = this.damage; // Use the updated damage value
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean isHandEmpty = p.hand.size() == 1 && p.hand.contains(this);
        boolean isDrawPileEmpty = p.drawPile.isEmpty();
        boolean isDiscardPileEmpty = p.discardPile.isEmpty();
        return isHandEmpty || isDrawPileEmpty || isDiscardPileEmpty;
    }

    @Override
    public void upp() {
        upgradeDamage(5); // Increase base damage by 5 upon upgrade
    }

    private int calculateBaseDamage(AbstractPlayer p) {
        int damage = baseDamage;
        boolean isDrawPileEmpty = p.drawPile.isEmpty();

        if (isDrawPileEmpty) {
            damage += baseDamage * 2; // Adjust for triple the baseDamage when the draw pile is empty
        }
        return damage;
    }

    @Override
    public void applyPowers() {
        int tempBaseDamage = this.baseDamage;
        this.baseDamage = calculateBaseDamage(AbstractDungeon.player);
        super.applyPowers(); // Apply game powers like Strength, Weakness, etc.
        this.baseDamage = tempBaseDamage; // Restore original baseDamage
        this.rawDescription = DESCRIPTION.replace("!D!", String.valueOf(this.damage)); // Update displayed damage
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tempBaseDamage = this.baseDamage;
        this.baseDamage = calculateBaseDamage(AbstractDungeon.player);
        super.calculateCardDamage(mo); // Apply game mechanics to this.damage
        this.baseDamage = tempBaseDamage; // Restore original baseDamage
        this.rawDescription = DESCRIPTION.replace("!D!", String.valueOf(this.damage)); // Update displayed damage
        initializeDescription();
    }

    private boolean shouldGlow(AbstractPlayer p) {
        return p.hand.size() == 1 && p.hand.contains(this) || p.drawPile.isEmpty() || p.discardPile.isEmpty();
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player != null) {
            this.glowColor = shouldGlow(AbstractDungeon.player) ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}
