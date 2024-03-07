package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.ArrayList;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
@NoPools
@NoCompendium
public class ReroutingAttack extends AbstractSwappableCard {
    public static final String ID = makeID("ReroutingAttack");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ReroutingAttack() {
        this(new ReroutingBlock(null));
    }

    public ReroutingAttack(AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, TEAL_COLOR);
        this.baseMagicNumber = 3;
        if (linkedCard == null) {
            this.setLinkedCard(new ReroutingBlock(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> skillCards = new ArrayList<>();
        for (AbstractCard card : p.hand.group) {
            // Ensure it does not add itself to the list of cards to discard
            if (card.type == AbstractCard.CardType.SKILL && card != this) {
                skillCards.add(card);
            }
        }

        // Discard a random skill card if there are any available
        if (!skillCards.isEmpty()) {
            AbstractCard cardToDiscard = skillCards.get(AbstractDungeon.cardRandomRng.random(0, skillCards.size() - 1));
            p.hand.moveToDiscardPile(cardToDiscard); // Moves the selected card to the discard pile
            cardToDiscard.triggerOnManualDiscard(); // Triggers any effects related to discarding
        }

        // This part of the effect plays regardless of whether a skill card was discarded
        int enemyCount = (int) AbstractDungeon.getMonsters().monsters.stream().filter(monster -> !monster.isDeadOrEscaped()).count();
        // Apply Vigor based on the number of enemies
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VigorPower(p, enemyCount * this.baseMagicNumber), enemyCount * this.baseMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
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
        return new ReroutingAttack(null);
    }
}
