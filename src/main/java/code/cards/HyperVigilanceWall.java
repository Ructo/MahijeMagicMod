package code.cards;

import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;

public class HyperVigilanceWall extends AbstractFlipCard {
    public static final String ID = makeID("HyperVigilanceWall");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HyperVigilanceWall() {
        this(new HyperVigilanceRepel(null));
    }
    public HyperVigilanceWall(AbstractFlipCard linkedCard) {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        baseBlock = 30;
        this.exhaust = true;
        initializeDescription();
        if (linkedCard == null) {
            this.setLinkedCard(new HyperVigilanceRepel(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck() ;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.player != null && !AbstractDungeon.isScreenUp) {
            AbstractCard newCard = this.cardsToPreview.makeStatEquivalentCopy();
            AbstractDungeon.actionManager.addToBottom(new FlipCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HyperVigilanceWall(null);
    }
}
