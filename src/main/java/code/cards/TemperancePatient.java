

package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;


import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
@NoCompendium
@NoPools

public class TemperancePatient extends AbstractSwappableCard {
    public static final String ID = makeID("TemperancePatient");
    public TemperancePatient() {
        this(new TemperanceSpiteful(null));
    }
    public TemperancePatient(AbstractSwappableCard linkedCard) {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        this.baseBlock = this.block = 3;
        this.exhaust = true;
        if (linkedCard == null) {
            this.setLinkedCard(new TemperanceSpiteful(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int numAttacksAndCursesExhausted = 0;
        for (AbstractCard c : p.hand.group) {
            if ((c.type == CardType.ATTACK || c.type == CardType.CURSE) && c != this) {
                numAttacksAndCursesExhausted++;
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
            }
        }
        if (numAttacksAndCursesExhausted > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, numAttacksAndCursesExhausted), numAttacksAndCursesExhausted));
            blck();
            blck();
            blck();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
            this.exhaust = false;
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
        return new TemperancePatient(null); // This constructor call ensures mutual linkage.
    }
}
