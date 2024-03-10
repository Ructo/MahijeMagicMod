package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
import code.cards.cardvars.CustomTags;
import code.powers.RefractedPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;

@NoPools
@NoCompendium
public class RefractedShield extends AbstractFlipCard {
    public final static String ID = makeID("RefractedShield");
    public RefractedShield() {
        this(new Refraction(null));
    }
    public RefractedShield(AbstractFlipCard linkedCard) {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        tags.add(CustomTags.LASERS);
        if (linkedCard == null) {
            this.setLinkedCard(new Refraction(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    public void use(AbstractPlayer owner, AbstractMonster m) {

        applyToSelf(new RefractedPower(owner));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
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
        return new RefractedShield(null);
    }
}

