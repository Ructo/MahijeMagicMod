package code.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;

import static code.ModFile.makeID;

public class Experience extends AbstractEasyCard {
    public static final String ID = makeID("Experience");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public Experience() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 0;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = AbstractDungeon.player.exhaustPile.size(); // Set damage based on exhaust pile size
        calculateCardDamage(m); // Ensure damage is calculated before action

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, damageTypeForTurn), AttackEffect.FIRE));
    }

    @Override
    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.exhaustPile.size(); // Update damage based on current exhaust pile size
        super.applyPowers(); // Call super to calculate damage modifiers
        updateDescription(); // Ensure description is updated to reflect current damage
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION; // Reset description upon moving to discard
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1); // Reduced cost when upgraded
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription(); // Ensure upgraded description is displayed
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Experience();
    }


    public void updateDescription() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
}
