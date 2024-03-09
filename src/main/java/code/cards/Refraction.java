package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Refraction extends AbstractEasyCard {
    public static final String ID = makeID("Refraction");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Refraction() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 2;
        baseMagicNumber = 0;
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int lasersPlayed = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.cardID.equals(makeID("Lasers"))) {
                lasersPlayed++;
            }
        }

        if (lasersPlayed > 0) {
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.miscRng);
            int totalDamage = lasersPlayed * this.damage;
            this.baseMagicNumber = totalDamage;
            this.magicNumber = this.baseMagicNumber;
            this.isMagicNumberModified = true;
            updateDescription(lasersPlayed); // Update the card's description
            for (int i = 0; i < lasersPlayed; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(randomMonster, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Refraction();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            initializeDescription();
        }
    }

    private void updateDescription(int lasersPlayed) {
        int totalDamage = lasersPlayed * this.damage;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription.replace("!D!", String.valueOf(this.damage));
        this.rawDescription = this.rawDescription.replace("!M!", String.valueOf(totalDamage));
        this.rawDescription = this.rawDescription.replace("!L!", String.valueOf(lasersPlayed));
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int lasersPlayed = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.cardID.equals(makeID("Lasers"))) {
                lasersPlayed++;
            }
        }
        updateDescription(lasersPlayed);
    }
}