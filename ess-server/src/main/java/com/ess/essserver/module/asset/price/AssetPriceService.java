package com.ess.essserver.module.asset.price;

import com.ess.essserver.module.asset.AssetEntity;
import com.ess.essserver.module.asset.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetPriceService {

    private final AssetPriceRepository assetPriceRepository;
    private final AssetRepository assetRepository;
    private final AssetPriceMapper assetPriceMapper;

    public List<AssetPriceResponseDTO> getAllAssetPrices(Long assetId) {
        return assetPriceRepository.findAllByAsset(assetRepository.getReferenceById(assetId)).stream()
                .map(assetPriceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AssetPriceResponseDTO getAssetPriceById(Long id, Long assetId) {
        AssetPriceEntity assetPrice = assetPriceRepository.findFirstByIdAndAsset(id, assetRepository.getReferenceById(assetId))
                .orElseThrow(() -> new RuntimeException("Asset Price not found with ID: " + id));
        return assetPriceMapper.toResponseDTO(assetPrice);
    }

    public AssetPriceResponseDTO createAssetPrice(Long assetId, AssetPriceRequestDTO dto) {
        AssetEntity assetEntity = assetRepository.getReferenceById(assetId);
        AssetPriceEntity assetPrice = assetPriceMapper.toEntity(assetEntity, dto);
        assetPrice = assetPriceRepository.save(assetPrice);
        return assetPriceMapper.toResponseDTO(assetPrice);
    }

    public AssetPriceResponseDTO updateAssetPriceByIdAndAssetId(Long id, Long assetId, AssetPriceRequestDTO dto) {
        AssetPriceEntity assetPrice = assetPriceRepository.findFirstByIdAndAsset(id, assetRepository.getReferenceById(assetId))
                .orElseThrow(() -> new RuntimeException("Asset Price not found with ID: " + id));

        assetPrice.setPrice(dto.getPrice());
        assetPrice.setStatus(dto.getStatus());

        assetPrice = assetPriceRepository.save(assetPrice);
        return assetPriceMapper.toResponseDTO(assetPrice);
    }

    public void deleteAssetPriceByIdAndAssetId(Long id, Long assetId) {
        AssetPriceEntity assetPrice = assetPriceRepository.findFirstByIdAndAsset(id, assetRepository.getReferenceById(assetId))
                .orElseThrow(() -> new RuntimeException("Asset Price not found with ID: " + id));
        assetPriceRepository.delete(assetPrice);
    }
}
