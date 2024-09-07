package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.PromotionalOffer;
import org.ecomoveV1.models.enums.OfferStatus;
import org.ecomoveV1.models.enums.ReductionType;
import org.ecomoveV1.repositories.PromotionalOfferRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PromotionService {

    private final PromotionalOfferRepository promotionalOfferRepository;

    public PromotionService(PromotionalOfferRepository promotionalOfferRepository) {
        this.promotionalOfferRepository = promotionalOfferRepository;
    }

    public void addPromotion(UUID contractId,String offerName, String description, LocalDate startDate, LocalDate endDate,
                             ReductionType reductionType, BigDecimal reductionValue, String conditions,
                             OfferStatus offerStatus) {
        UUID id = UUID.randomUUID();
        PromotionalOffer promotionalOffer = new PromotionalOffer(id,contractId, offerName, description, startDate, endDate,
                reductionType, reductionValue, conditions, offerStatus);
        promotionalOfferRepository.addPromotion(promotionalOffer);
    }


    public List<PromotionalOffer> getAllPromotionalOffers() {
        return promotionalOfferRepository.getAllPromotionalOffers();
    }

    public PromotionalOffer getPromotionalOfferById(UUID id) {
        return promotionalOfferRepository.getPromotionalOfferById(id);
    }

    public void updatePromotionalOffer(PromotionalOffer offer) {
        promotionalOfferRepository.updatePromotionalOffer(offer);
    }

    public void deletePromotionalOffer(UUID id) {
        promotionalOfferRepository.deletePromotionalOffer(id);
    }


}
