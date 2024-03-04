//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package code.cards.abstractCards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractModdedCard extends CustomCard {
    public int secondMagicNumber = -1;
    public int baseSecondMagicNumber = -1;
    public boolean upgradedSecondMagicNumber;
    public boolean isSecondMagicNumberModified;
    public int info = -1;
    public int baseInfo = -1;
    public boolean upgradedInfo;
    public boolean isInfoModified;
    public int invertedNumber = -1;
    public int baseInvertedNumber = -1;
    public boolean upgradedInvertedNumber;
    public boolean isInvertedNumberModified;
    public int currentAmmo = -1;
    public int maxAmmo = -1;
    public int baseMaxAmmo = -1;
    public boolean upgradedMaxAmmo;
    public boolean isCurrentAmmoModified;
    public boolean isMaxAmmoModified;
    public CardStrings cardStrings;
    public String DESCRIPTION;
    public String UPGRADE_DESCRIPTION;
    public String[] EXTENDED_DESCRIPTION;
    public String NAME;

    public AbstractModdedCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.isCostModified = false;
        this.isCostModifiedForTurn = false;
        this.isDamageModified = false;
        this.isBlockModified = false;
        this.isMagicNumberModified = false;
        this.isSecondMagicNumberModified = false;
        this.isInfoModified = false;
        this.isInvertedNumberModified = false;
        this.isCurrentAmmoModified = false;
        this.isMaxAmmoModified = false;
        CommonKeywordIconsField.useIcons.set(this, true);
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.DESCRIPTION = this.cardStrings.DESCRIPTION;
        this.UPGRADE_DESCRIPTION = this.cardStrings.UPGRADE_DESCRIPTION;
        this.EXTENDED_DESCRIPTION = this.cardStrings.EXTENDED_DESCRIPTION;
        this.NAME = this.cardStrings.NAME;
        this.initializeDescription();
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.upgradedSecondMagicNumber) {
            this.secondMagicNumber = this.baseSecondMagicNumber;
            this.isSecondMagicNumberModified = true;
        }

        if (this.upgradedInfo) {
            this.info = this.baseInfo;
            this.isInfoModified = true;
        }

        if (this.upgradedInvertedNumber) {
            this.invertedNumber = this.baseInvertedNumber;
            this.isInvertedNumberModified = true;
        }

        if (this.upgradedMaxAmmo) {
            this.maxAmmo = this.baseMaxAmmo;
            this.isMaxAmmoModified = true;
            this.isCurrentAmmoModified = true;
        }

    }

    public void upgradeSecondMagicNumber(int amount) {
        this.baseSecondMagicNumber += amount;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.upgradedSecondMagicNumber = true;
    }

    public void upgradeInfo(int amount) {
        this.baseInfo += amount;
        this.info = this.baseInfo;
        this.upgradedInfo = true;
    }

    public void upgradeInvertedNumber(int amount) {
        this.baseInvertedNumber += amount;
        this.invertedNumber = this.baseInvertedNumber;
        this.upgradedInvertedNumber = true;
    }

    public void upgradeMaxAmmo(int amount) {
        this.baseMaxAmmo += amount;
        this.maxAmmo = this.baseMaxAmmo;
        this.currentAmmo += amount;
        this.upgradedMaxAmmo = true;
    }

    public void resetAttributes() {
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.isSecondMagicNumberModified = false;
        this.info = this.baseInfo;
        this.isInfoModified = false;
        this.invertedNumber = this.baseInvertedNumber;
        this.isInvertedNumberModified = false;
        this.maxAmmo = this.baseMaxAmmo;
        this.isMaxAmmoModified = false;
        super.resetAttributes();
    }
}
