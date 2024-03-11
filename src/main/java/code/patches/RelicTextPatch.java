package code.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

//Patch for making text easier to read on character select screen.
@SpirePatch(
        clz = CharacterOption.class,
        method = "renderRelics"
)
public class RelicTextPatch {
    @SpirePatch2(clz = CharacterOption.class, method = "renderRelics")
    public static class OutlineText {
        @SpireInstrumentPatch
        public static ExprEditor plz() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.getClassName().equals(FontHelper.class.getName()) && f.getFieldName().equals("tipBodyFont")) {
                        f.replace("$_="+FontHelper.class.getName()+".tipHeaderFont;");
                    }
                }
            };
        }
    }
}