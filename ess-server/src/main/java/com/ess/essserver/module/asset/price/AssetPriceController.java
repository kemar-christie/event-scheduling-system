package com.ess.essserver.module.asset.price;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Asset Price Management", description = "CRUD APIs for managing asset prices")
@RestController
@RequestMapping("/api/assets/{assetId}/prices")
@RequiredArgsConstructor
public class AssetPriceController {

    private final AssetPriceService assetPriceService;

    @Operation(summary = "Get all asset prices")
    @GetMapping
    public ResponseEntity<List<AssetPriceResponseDTO>> getAllAssetPrices(@PathVariable Long assetId) {
        return ResponseEntity.ok(assetPriceService.getAllAssetPrices(assetId));
    }

    @Operation(summary = "Get asset price by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AssetPriceResponseDTO> getAssetPriceById(@PathVariable Long id, @PathVariable Long assetId) {
        return ResponseEntity.ok(assetPriceService.getAssetPriceById(id, assetId));
    }

    @Operation(summary = "Create a new asset price")
    @PostMapping
    public ResponseEntity<AssetPriceResponseDTO> createAssetPrice(
            @Valid @RequestBody AssetPriceRequestDTO dto, @PathVariable Long assetId) {
        return ResponseEntity.ok(assetPriceService.createAssetPrice(assetId, dto));
    }

    @Operation(summary = "Update an existing asset price")
    @PutMapping("/{id}")
    public ResponseEntity<AssetPriceResponseDTO> updateAssetPrice(
            @PathVariable Long id,
            @Valid @RequestBody AssetPriceRequestDTO dto, @PathVariable Long assetId) {
        return ResponseEntity.ok(assetPriceService.updateAssetPriceByIdAndAssetId(id, assetId, dto));
    }

    @Operation(summary = "Delete an asset price by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetPrice(@PathVariable Long id, @PathVariable Long assetId) {
        assetPriceService.deleteAssetPriceByIdAndAssetId(id, assetId);
        return ResponseEntity.noContent().build();
    }
}