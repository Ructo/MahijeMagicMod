package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import code.cards.HyperFocusStrength;
import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import code.powers.StrFixation;
import code.powers.DexFixation;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;
import static code.util.Wiz.atb;
@NoPools
@NoCompendium
public class HyperFocusDexterity extends AbstractSwappableCard {
    public final static String ID = makeID("HyperFocusDexterity");
    public HyperFocusDexterity() {
        this(new HyperFocusStrength(null));
    }
    public HyperFocusDexterity(AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        magicNumber = baseMagicNumber = 3;
        if (linkedCard == null) {
            this.setLinkedCard(new HyperFocusStrength(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // Check if the player already has StrFixation or DexFixation and remove them
        if (p.hasPower(StrFixation.POWER_ID)) {
            atb(new RemoveSpecificPowerAction(p, p, StrFixation.POWER_ID));
        }
        if (p.hasPower(DexFixation.POWER_ID)) {
            atb(new RemoveSpecificPowerAction(p, p, DexFixation.POWER_ID));
        }

        // Apply DexFixation
        applyToSelf(new DexFixation(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
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
        return new HyperFocusDexterity(null);
    }
}

