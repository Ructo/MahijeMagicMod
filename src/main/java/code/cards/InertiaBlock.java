package code.cards;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
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
@NoPools
@NoCompendium
public class InertiaBlock extends AbstractSwappableCard {
    public static final String ID = makeID("InertiaBlock");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public InertiaBlock() {
        this(new InertiaVigor(null));
    }
    public InertiaBlock(AbstractSwappableCard linkedCard) {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        if (linkedCard == null) {
            this.setLinkedCard(new InertiaVigor(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ScalingBlockPower(p, this.magicNumber));
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
        return new InertiaBlock(null);
    }
}
