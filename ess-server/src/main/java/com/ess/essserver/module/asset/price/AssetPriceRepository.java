package com.ess.essserver.module.asset.price;

import com.ess.essserver.module.asset.AssetEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetPriceRepository extends JpaRepository<AssetPriceEntity, Long> {
    List<AssetPriceEntity> findAllByAsset(AssetEntity asset);
    Optional<AssetPriceEntity> findFirstByIdAndAsset(Long id, AssetEntity asset);
    Optional<AssetPriceEntity> deleteAssetPriceEntityByIdAndAsset(Long id, AssetEntity asset);
}