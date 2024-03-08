package code.cards;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;

import static code.ModFile.makeID;
import static code.util.Wiz.*;

public class CreativeMind extends AbstractEasyCard {

    public final static String ID = makeID("CreativeMind");
    // intellij stuff skill, self, uncommon

    public CreativeMind() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
this.exhaust = true;
     }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> myCardsList = new ArrayList<>();
        ArrayList<AbstractCard> eligibleCardsList = getCardsMatchingPredicate(c -> c.cost == 0, true);
        Collections.shuffle(eligibleCardsList);
        for (int i = 0; i < 3; i++) {
            CardModifierManager.addModifier(eligibleCardsList.get(i), new EtherealMod());
            CardModifierManager.addModifier(eligibleCardsList.get(i), new ExhaustMod());
            myCardsList.add(eligibleCardsList.get(i));
        }
        atb(new SelectCardsAction(myCardsList, 1, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            att(new MakeTempCardInHandAction(cards.get(0), 1, true));
        }));

    }
        public void upgrade() {
            if (!this.upgraded) {
                this.upgradeName();
                this.exhaust = false;
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                this.initializeDescription();
            }
        }
    }