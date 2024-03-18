package code.cards;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
import static code.util.Wiz.*;

public class AnxiousMind extends AbstractFlipCard {

    public final static String ID = makeID("AnxiousMind");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AnxiousMind() {
        this(new CreativeMind(null));
    }

    public AnxiousMind(AbstractFlipCard linkedCard) {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF, TEAL_COLOR);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 1;
        if (linkedCard == null) {
            this.setLinkedCard(new CreativeMind(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > magicNumber) {
            AbstractDungeon.actionManager.addToBottom(
                    new DiscardAction(p, p, 1, true)
            );
        }
    }

            public void upgrade () {
                if (!this.upgraded) {
                    this.upgradeName();
                    this.exhaust = false;
                    this.cardsToPreview.upgrade();
                    this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                    this.initializeDescription();
                }
            }

            @Override
            public void onRightClick () {
                if (AbstractDungeon.player != null && !AbstractDungeon.isScreenUp) {
                    AbstractCard newCard = this.cardsToPreview.makeStatEquivalentCopy();
                    AbstractDungeon.actionManager.addToBottom(new FlipCardsAction(this, newCard));
                }
            }

            @Override
            public AbstractCard makeCopy () {
                return new AnxiousMind(null);
            }
    private boolean shouldGlow(AbstractPlayer p) {
        return AbstractDungeon.actionManager.cardsPlayedThisTurn.size() < magicNumber;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player != null) {
            this.glowColor = shouldGlow(AbstractDungeon.player) ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }

}
