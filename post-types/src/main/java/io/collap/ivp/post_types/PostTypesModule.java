package io.collap.ivp.post_types;

import io.collap.bryg.EnvironmentConfigurator;
import io.collap.bryg.EnvironmentCreator;
import io.collap.bryg.ModuleSourceLoader;
import io.collap.bryg.compiler.resolver.ClassResolver;
import io.collap.bryg.compiler.resolver.PackageFilter;
import io.collap.bryg.environment.Environment;
import io.collap.bryg.loader.SourceLoader;
import io.collap.bryg.model.GlobalVariableModel;
import io.collap.ivp.game_data.GameDataModule;
import io.collap.ivp.game_data.entity.hearthstone.HearthstoneClass;
import io.collap.ivp.post_types.entity.hearthstone.DeckBudget;
import io.collap.ivp.post_types.entity.hearthstone.DeckData;
import io.collap.ivp.post_types.type.hearthstone.DeckType;
import io.collap.plugin.Module;
import io.collap.std.post.PostModule;
import io.collap.std.format.Format;
import org.hibernate.cfg.Configuration;

import javax.annotation.Nullable;

public class PostTypesModule extends Module implements EnvironmentConfigurator {

    private static final String VERSION = "0.1.1";
    private static final String ARTIFACT_NAME = "ivp-post-types-" + VERSION;

    private Environment bryg;

    @Override
    public void initialize () {
        bryg = new EnvironmentCreator (collap, this).create ();

        PostModule module = (PostModule) collap.getPluginManager ().getPlugins ().get ("std-post");

        /* Add post types. */
        module.addPostType (new DeckType (collap, bryg));
    }

    @Override
    public void destroy () {

    }

    @Override
    public void configureHibernate (Configuration cfg) {
        cfg.addAnnotatedClass (DeckData.class);
    }

    @Override
    public SourceLoader getSourceLoader () {
        return new ModuleSourceLoader (this);
    }

    @Override
    public void configureConfiguration (io.collap.bryg.compiler.Configuration configuration) {

    }

    @Override
    public void configureClassResolver (ClassResolver classResolver) {
        classResolver.includeJar (GameDataModule.ARTIFACT_NAME + ".jar");

        PackageFilter filter = classResolver.getRootPackageFilter ();
        filter.addSubpackageFilter (Deck.class.getPackage ().getName ());
        filter.addSubpackageFilter (DeckBudget.class.getPackage ().getName ());
        filter.addSubpackageFilter (DeckType.class.getPackage ().getName ());
        filter.addSubpackageFilter (HearthstoneClass.class.getPackage ().getName ());
    }

    @Override
    public void configureGlobalVariableModel (GlobalVariableModel globalVariableModel) {
        globalVariableModel.declareVariable ("format", Format.class, new Format ());
    }

    @Nullable
    @Override
    public String getArtifactName () {
        return ARTIFACT_NAME;
    }

}
