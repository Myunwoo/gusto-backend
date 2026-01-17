package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientCollectionItemJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientCollectionJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.IngredientCollectionUseCase;
import com.gustoexpedition.ingredient.entity.IngredientCollectionEntity;
import com.gustoexpedition.ingredient.entity.IngredientCollectionItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.gustoexpedition.ingredient.application.service
 * fileName : IngredientCollectionService
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 컬렉션 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class IngredientCollectionService implements IngredientCollectionUseCase {

  private final IngredientCollectionJpaRepository ingredientCollectionJpaRepository;
  private final IngredientCollectionItemJpaRepository ingredientCollectionItemJpaRepository;
  private final IngredientJpaRepository ingredientJpaRepository;

  @Override
  @Transactional
  public CreateCollectionResDto createCollection(CreateCollectionReqDto req) {
    // 1. 입력 검증
    if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
      throw new GustoException("COLLECTION001"); // 컬렉션 제목은 필수입니다.
    }

    // 2. Collection 엔티티 생성 및 저장
    IngredientCollectionEntity collectionEntity = new IngredientCollectionEntity(
        req.getTitle().trim(),
        req.getNote());
    IngredientCollectionEntity savedCollection = ingredientCollectionJpaRepository.save(collectionEntity);

    // 3. 응답 DTO 생성
    return new CreateCollectionResDto(
        savedCollection.getCollectionId(),
        savedCollection.getTitle(),
        savedCollection.getNote(),
        savedCollection.getCreatedAt());
  }

  @Override
  @Transactional(readOnly = true)
  public SelectCollectionResDto selectCollectionById(Long collectionId) {
    // 1. Collection 조회
    IngredientCollectionEntity collection = ingredientCollectionJpaRepository.findById(collectionId)
        .orElse(null);

    if (collection == null) {
      return null;
    }

    // 2. 아이템 목록 조회 (순서대로)
    List<IngredientCollectionItemEntity> itemEntities = ingredientCollectionItemJpaRepository
        .findByCollectionIdOrderByAddedOrderAsc(collectionId);

    List<CollectionItemDto> itemDtos = itemEntities.stream()
        .map(item -> new CollectionItemDto(
            item.getCollectionItemId(),
            item.getIngredientId(),
            item.getAddedOrder(),
            item.getCreatedAt()))
        .collect(Collectors.toList());

    // 3. 응답 DTO 생성
    return new SelectCollectionResDto(
        collection.getCollectionId(),
        collection.getTitle(),
        collection.getNote(),
        itemDtos,
        collection.getCreatedAt(),
        collection.getUpdatedAt());
  }

  @Override
  @Transactional
  public UpdateCollectionResDto updateCollection(UpdateCollectionReqDto req) {
    // 1. Collection 조회
    IngredientCollectionEntity collection = ingredientCollectionJpaRepository.findById(req.getCollectionId())
        .orElseThrow(() -> new GustoException("COLLECTION005")); // 컬렉션을 찾을 수 없습니다.

    // 2. 입력 검증
    if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
      throw new GustoException("COLLECTION001"); // 컬렉션 제목은 필수입니다.
    }

    // 3. 엔티티 수정
    collection.setTitle(req.getTitle().trim());
    collection.setNote(req.getNote());

    // 4. 저장
    IngredientCollectionEntity updatedCollection = ingredientCollectionJpaRepository.save(collection);

    // 5. 응답 DTO 생성
    return new UpdateCollectionResDto(
        updatedCollection.getCollectionId(),
        updatedCollection.getTitle(),
        updatedCollection.getNote(),
        updatedCollection.getUpdatedAt());
  }

  @Override
  @Transactional
  public DeleteCollectionResDto deleteCollection(Long collectionId) {
    // 1. Collection 조회
    IngredientCollectionEntity collection = ingredientCollectionJpaRepository.findById(collectionId)
        .orElseThrow(() -> new GustoException("COLLECTION005")); // 컬렉션을 찾을 수 없습니다.

    // 2. Collection 삭제 (CASCADE로 아이템도 함께 삭제됨)
    ingredientCollectionJpaRepository.delete(collection);

    // 3. 응답 DTO 생성
    return new DeleteCollectionResDto(
        collectionId,
        "컬렉션이 성공적으로 삭제되었습니다.");
  }

  @Override
  @Transactional
  public AddCollectionItemResDto addCollectionItem(AddCollectionItemReqDto req) {
    // 1. Collection 존재 확인
    ingredientCollectionJpaRepository.findById(req.getCollectionId())
        .orElseThrow(() -> new GustoException("COLLECTION005")); // 컬렉션을 찾을 수 없습니다.

    // 2. 재료 존재 확인
    ingredientJpaRepository.findById(req.getIngredientId())
        .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

    // 3. 중복 체크 (같은 컬렉션에 같은 재료가 이미 있는지 확인)
    ingredientCollectionItemJpaRepository
        .findByCollectionIdAndIngredientId(req.getCollectionId(), req.getIngredientId())
        .ifPresent(existing -> {
          throw new GustoException("COLLECTION006"); // 이미 컬렉션에 포함된 재료입니다.
        });

    // 4. 순서 결정 (addedOrder가 없으면 마지막 순서로)
    Integer addedOrder = req.getAddedOrder();
    if (addedOrder == null) {
      List<IngredientCollectionItemEntity> existingItems = ingredientCollectionItemJpaRepository
          .findByCollectionIdOrderByAddedOrderAsc(req.getCollectionId());
      addedOrder = existingItems.isEmpty() ? 1 : existingItems.size() + 1;
    }

    // 5. CollectionItem 엔티티 생성 및 저장
    IngredientCollectionItemEntity itemEntity = new IngredientCollectionItemEntity(
        req.getCollectionId(),
        req.getIngredientId(),
        addedOrder);
    IngredientCollectionItemEntity savedItem = ingredientCollectionItemJpaRepository.save(itemEntity);

    // 6. 응답 DTO 생성
    return new AddCollectionItemResDto(
        savedItem.getCollectionItemId(),
        savedItem.getCollectionId(),
        savedItem.getIngredientId(),
        savedItem.getAddedOrder(),
        savedItem.getCreatedAt());
  }

  @Override
  @Transactional
  public UpdateCollectionItemOrderResDto updateCollectionItemOrder(UpdateCollectionItemOrderReqDto req) {
    // 1. CollectionItem 조회
    IngredientCollectionItemEntity item = ingredientCollectionItemJpaRepository.findById(req.getCollectionItemId())
        .orElseThrow(() -> new GustoException("COLLECTION007")); // 컬렉션 아이템을 찾을 수 없습니다.

    // 2. 순서 수정
    item.setAddedOrder(req.getAddedOrder());
    IngredientCollectionItemEntity updatedItem = ingredientCollectionItemJpaRepository.save(item);

    // 3. 응답 DTO 생성
    return new UpdateCollectionItemOrderResDto(
        updatedItem.getCollectionItemId(),
        updatedItem.getCollectionId(),
        updatedItem.getIngredientId(),
        updatedItem.getAddedOrder());
  }

  @Override
  @Transactional
  public DeleteCollectionItemResDto deleteCollectionItem(Long collectionItemId) {
    // 1. CollectionItem 조회
    IngredientCollectionItemEntity item = ingredientCollectionItemJpaRepository.findById(collectionItemId)
        .orElseThrow(() -> new GustoException("COLLECTION007")); // 컬렉션 아이템을 찾을 수 없습니다.

    // 2. CollectionItem 삭제
    ingredientCollectionItemJpaRepository.delete(item);

    // 3. 응답 DTO 생성
    return new DeleteCollectionItemResDto(
        collectionItemId,
        "컬렉션 아이템이 성공적으로 삭제되었습니다.");
  }
}
