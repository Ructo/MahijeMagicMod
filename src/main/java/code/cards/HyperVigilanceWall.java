package code.cards;

import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;

public class HyperVigilanceWall extends AbstractSwappableCard {
    public static final String ID = makeID("HyperVigilanceWall");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HyperVigilanceWall() {
        this(new HyperVigilanceRepel(null));
    }
    public HyperVigilanceWall(AbstractSwappableCard linkedCard) {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        baseBlock = 40;
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
            AbstractDungeon.actionManager.addToBottom(new SwapCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HyperVigilanceWall(null);
    }
}
