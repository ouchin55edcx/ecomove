package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.PromotionalOffer;

import java.util.List;
import java.util.UUID;

public interface PromotionalOfferRepository {
    void addPromotion(PromotionalOffer offer);
    List<PromotionalOffer> getAllPromotionalOffers();
    PromotionalOffer getPromotionalOfferById(UUID id);
    void updatePromotionalOffer(PromotionalOffer offer);
    void deletePromotionalOffer(UUID id);


}
