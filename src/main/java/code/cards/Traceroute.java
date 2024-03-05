package code.cards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Traceroute extends AbstractEasyCard {
    public final static String ID = makeID("Traceroute"); // Make sure to register this ID properly in your mod setup.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Traceroute() {
        super(ID, 2, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhumeAction(false));
    }

    @Override
    public void upp() {
            upgradeName();
            upgradeBaseCost(1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Traceroute();
    }
}
