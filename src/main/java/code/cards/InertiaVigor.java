package code.cards;
import code.cards.InertiaBlock;
import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.powers.ScalingVigorPower;
import code.powers.ScalingBlockPower;
import code.cards.AbstractEasyCard;
import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;

public class InertiaVigor extends AbstractSwappableCard {
    public static final String ID = makeID("InertiaVigor");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public InertiaVigor() {this(new InertiaBlock(null)); }

    public InertiaVigor(AbstractSwappableCard linkedCard) {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        this.magicNumber = this.baseMagicNumber = 1;
        if (linkedCard == null) {
            this.setLinkedCard(new InertiaBlock(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ScalingVigorPower(p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.player != null && !AbstractDungeon.isScreenUp) {
            AbstractCard newCard = this.cardsToPreview.makeStatEquivalentCopy();
            AbstractDungeon.actionManager.addToBottom(new SwapCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new InertiaVigor(null);
    }
}
