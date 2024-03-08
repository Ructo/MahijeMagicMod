package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, TEAL_COLOR);
        this.baseDamage = 7;
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

        // Calculate the number of monsters
        int monsterCount = (int) AbstractDungeon.getMonsters().monsters.stream().filter(monster -> !monster.isDeadOrEscaped()).count();

        // Attack the targeted monster multiple times, dealing damage equal to the base damage
        if (m != null && !m.isDeadOrEscaped()) {
            for (int i = 0; i < monsterCount; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(
                                m,
                                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                                AbstractGameAction.AttackEffect.BLUNT_LIGHT
                        )
                );
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2); // Increase damage by 2
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