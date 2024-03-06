package code.relics;

import code.CharacterFile;
import code.cards.Flail;
import code.cards.Struggle;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static code.ModFile.makeID;

public class StruggleBus extends AbstractEasyRelic {
    public static final String ID = makeID("StruggleBus");

    public StruggleBus() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, CharacterFile.Enums.TEAL_COLOR);
        this.counter = 0;
    }

        @Override
        public void onExhaust (AbstractCard card){
            if (card.cardID.equals(Struggle.ID)) {
                this.counter++;
                if (this.counter >= 3) {
                    this.counter = 0; // Reset counter
                    flash(); // Additional visual feedback if needed
                    AbstractCard cardToAdd = new Flail();
                    addToBot(new MakeTempCardInHandAction(cardToAdd, 1));
                }
            }
        }

        @Override
        public String getUpdatedDescription () {
            return DESCRIPTIONS[0]; // Your description text here
        }
    }
