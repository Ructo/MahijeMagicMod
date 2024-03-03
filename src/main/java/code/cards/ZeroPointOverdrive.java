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
        baseDamage = 15;
        magicNumber = baseMagicNumber = 30; // Ensure magicNumber is initialized correctly
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int damage = calculateDamage();
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean isOnlyCardInHand = p.hand.size() == 1 && p.hand.contains(this);
        boolean isDrawPileEmpty = p.drawPile.isEmpty();
        return isOnlyCardInHand || isDrawPileEmpty;
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }

    private boolean shouldGlow() {
        if (AbstractDungeon.player != null) {
            boolean isOnlyCardInHand = AbstractDungeon.player.hand.size() == 1 && AbstractDungeon.player.hand.contains(this);
            boolean isDrawPileEmpty = AbstractDungeon.player.drawPile.isEmpty();
            return isOnlyCardInHand || isDrawPileEmpty;
        }
        return false;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = shouldGlow() ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    private int calculateDamage() {
        if (AbstractDungeon.player != null) {
            boolean isHandOrDrawPileEmpty = AbstractDungeon.player.hand.isEmpty() || AbstractDungeon.player.drawPile.isEmpty();
            return baseDamage + (isHandOrDrawPileEmpty ? magicNumber : 0);
        }
        return baseDamage; // Default damage if player state can't be accessed
    }

    public void update() {
        super.update();
        int damage = calculateDamage();
        this.rawDescription = DESCRIPTION.replace("!D!", String.valueOf(damage));
        initializeDescription();
    }
}
