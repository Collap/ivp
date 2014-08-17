package io.collap.ivp.post_types;

import io.collap.ivp.post_types.entity.hearthstone.DeckData;
import io.collap.ivp.post_types.type.hearthstone.DeckType;
import io.collap.plugin.Module;
import io.collap.std.post.PostModule;
import io.collap.template.TemplateRenderer;
import org.hibernate.cfg.Configuration;

public class PostTypesModule extends Module {

    private TemplateRenderer renderer;

    @Override
    public void initialize () {
        renderer = new TemplateRenderer (this);

        PostModule module = (PostModule) collap.getPluginManager ().getPlugins ().get ("std-post");

        /* Add post types. */
        module.addPostType (new DeckType (collap, renderer));
    }

    @Override
    public void destroy () {

    }

    @Override
    public void configureHibernate (Configuration cfg) {
        cfg.addAnnotatedClass (DeckData.class);
    }

}
