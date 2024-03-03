package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import code.actions.EasyModalChoiceAction;

import java.util.ArrayList;

import static code.ModFile.makeID;

public class Temperance extends AbstractEasyCard {
    public final static String ID = makeID("Temperance");
    public Temperance() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        baseDamage = 3;
        baseBlock = 3;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
        int numSkillsExhausted = 0;
        int numAttacksAndCursesExhausted = 0;

        // Separate cards into a list based on type and count skills, attacks, and curses
        for (AbstractCard c : p.hand.group) {
            if (c.type == AbstractCard.CardType.SKILL) {
                cardsToExhaust.add(c);
                numSkillsExhausted++;
            } else if (c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.CURSE) {
                cardsToExhaust.add(c);
                numAttacksAndCursesExhausted++;
            }
        }

        // Adjust the count of skills to exclude Temperance
        numSkillsExhausted = Math.max(0, numSkillsExhausted - 1);

        // Prompt the player to choose between Strength and Dexterity
        ArrayList<AbstractCard> choiceOptions = new ArrayList<>();
        int finalNumSkillsExhausted = numSkillsExhausted;
        choiceOptions.add(new EasyModalChoiceCard("Spiteful", "Exhaust all skills in hand NL Gain 1 Strength and attack all thrice for each", () -> {
            for (AbstractCard c : cardsToExhaust) {
                if (c.type == AbstractCard.CardType.SKILL) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
                }
            }
            if (finalNumSkillsExhausted > 0) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, finalNumSkillsExhausted), finalNumSkillsExhausted));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, false));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
            }
        }));
        int finalNumAttacksAndCursesExhausted = numAttacksAndCursesExhausted;
        choiceOptions.add(new EasyModalChoiceCard("Patient", "Exhaust all attacks and curses NL Gain 1 Dexterity and block thrice for each.", () -> {
            for (AbstractCard c : cardsToExhaust) {
                if (c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.CURSE) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
                }
            }
            if (finalNumAttacksAndCursesExhausted > 0) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, finalNumAttacksAndCursesExhausted), finalNumAttacksAndCursesExhausted));
                blck();
                blck();
                blck();
            }
        }));
        AbstractDungeon.actionManager.addToBottom(new EasyModalChoiceAction(choiceOptions));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeBlock(1);
    }
}