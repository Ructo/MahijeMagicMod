package code.cards;

import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
import code.cards.cardvars.CustomTags;
import code.powers.RefractingPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;


public class Refraction extends AbstractFlipCard {
    public final static String ID = makeID("Refraction");
    public Refraction() {
        this(new RefractedShield(null));
    }
    public Refraction(AbstractFlipCard linkedCard) {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        magicNumber = baseMagicNumber = 2;
        tags.add(CustomTags.LASERS);
        if (linkedCard == null) {
            this.setLinkedCard(new RefractedShield(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    public void use(AbstractPlayer owner, AbstractMonster m) {
        applyToSelf(new RefractingPower(owner));
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
        return new Refraction(null);
    }
}

