package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
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
@NoPools
@NoCompendium
public class HyperVigilanceRepel extends AbstractSwappableCard {
    public static final String ID = makeID("HyperVigilanceRepel");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HyperVigilanceRepel() {
        this(new HyperVigilanceWall(null));
    }
    public HyperVigilanceRepel(AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        baseMagicNumber = magicNumber = 1; // Used for the VigilantPower
        this.exhaust = true;
        initializeDescription();
        if (linkedCard == null) {
            this.setLinkedCard(new HyperVigilanceWall(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
        if (this.upgraded) {
        addToBot((AbstractGameAction)new ExhaustAction(1, false));
        } else {
            addToBot((AbstractGameAction)new ExhaustAction(1, true, false, false));
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
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
        return new HyperVigilanceRepel(null);
    }
}
