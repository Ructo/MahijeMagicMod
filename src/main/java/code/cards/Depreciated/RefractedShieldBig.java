package code.cards.Depreciated;

import code.cards.AbstractEasyCard;
import code.cards.cardvars.CustomTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static code.ModFile.makeID;

public class RefractedShieldBig extends AbstractEasyCard {
    public static final String ID = makeID("RefractedShield");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public RefractedShieldBig() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 3;
        this.exhaust = true;
        tags.add(CustomTags.LASERS);
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int lasersPlayed = countLasersPlayed();
        int effectiveBlock = Math.max(1, lasersPlayed / 2) * this.block;
        addToBot(new GainBlockAction(p, p, effectiveBlock));
        updateDescription(lasersPlayed);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            updateDescription(countLasersPlayed());
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        updateDescription(countLasersPlayed());
    }

    private void updateDescription(int lasersPlayed) {
        int effectiveBlock = Math.max(1, lasersPlayed / 2) * this.block;
        this.rawDescription = cardStrings.DESCRIPTION
                .replace("!B!", String.valueOf(effectiveBlock))
                .replace("!L!", String.valueOf(lasersPlayed));
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new RefractedShieldBig();
    }

    private int countLasersPlayed() {
        return (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream()
                .filter(c -> c.hasTag(CustomTags.LASERS))
                .count();
    }
}
