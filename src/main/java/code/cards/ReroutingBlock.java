package code.cards;

import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;

public class ReroutingBlock extends AbstractFlipCard {
    public static final String ID = makeID("ReroutingBlock");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public ReroutingBlock() {
        this(new ReroutingAttack(null));
    }

    public ReroutingBlock(AbstractFlipCard linkedCard) {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, TEAL_COLOR);
        this.baseBlock = this.block = 7; // Adjust base block as needed
        initializeDescription();
        if (linkedCard == null) {
            this.setLinkedCard(new ReroutingAttack(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> attackCards = new ArrayList<>();
        for (AbstractCard card : p.hand.group) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                attackCards.add(card);
            }
        }

        if (!attackCards.isEmpty()) {
            AbstractCard cardToDiscard = attackCards.get(AbstractDungeon.cardRandomRng.random(0, attackCards.size() - 1));
            p.hand.moveToDiscardPile(cardToDiscard);
            cardToDiscard.triggerOnManualDiscard();
        }

        int enemyCount = (int) AbstractDungeon.getMonsters().monsters.stream().filter(monster -> !monster.isDeadOrEscaped()).count();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.baseBlock * enemyCount));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            upgradeBlock(2); // Upgrade block as needed
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
        return new ReroutingBlock(null); // This constructor call ensures mutual linkage.
    }
}
