package io.collap.ivp.game_data.hearthstone;

import io.collap.controller.ModuleController;
import io.collap.controller.communication.Request;
import io.collap.controller.communication.Response;
import io.collap.ivp.game_data.entity.hearthstone.Card;
import org.hibernate.Session;

import java.io.IOException;

public class GetCard extends ModuleController {

    private long cardId;

    @Override
    public void initialize (Request request, String remainingPath) {
        super.initialize (request, remainingPath);

        cardId = Long.parseLong (remainingPath);
    }

    @Override
    public void doGet (Response response) throws IOException {
        Session session = module.getCollap ().getSessionFactory ().getCurrentSession ();

        Card card = (Card) session.get (Card.class, cardId);
        if (card == null) {
            response.getContentWriter ().write ("Card (Id: " + cardId + ") not found!");
            return;
        }

        response.getHeadWriter ().write ("<title>" + card.getName () + "</title>");
        response.getContentWriter ().write (card.getName ());
    }

}
