package np.com.naveenniraula.sahayatri;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class CustomContextWrapper extends ContextWrapper {

    public CustomContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context, Locale locale) {

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale);

            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);

            context = context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            configuration.setLocale(locale);
            context = context.createConfigurationContext(configuration);
        } else {

            configuration.locale = locale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

        return new ContextWrapper(context);
    }
}
