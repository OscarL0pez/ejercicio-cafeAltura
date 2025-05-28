package com.cafeteria.cafedealtura.dto;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Clase DTO para manejar respuestas paginadas en la API.
 * Esta clase encapsula tanto los datos de la página actual como los metadatos
 * necesarios para la navegación paginada en el cliente.
 * 
 * La paginación se implementa en todos los endpoints de listado (GET) de la
 * API:
 * - GET /api/cafes
 * - GET /api/customers
 * - GET /api/orders
 * 
 * Los parámetros de paginación son:
 * - page: número de página (0-based)
 * - size: tamaño de cada página
 * 
 * Ejemplo de uso:
 * GET /api/cafes?page=0&size=10
 * 
 * @param <T> Tipo de datos que se paginan (Cafe, Customer, Order, etc.)
 */
public class PaginatedResponse<T> {
    /**
     * Lista de elementos en la página actual
     */
    private List<T> content;

    /**
     * Número de página actual (0-based)
     */
    private int currentPage;

    /**
     * Número total de páginas disponibles
     */
    private int totalPages;

    /**
     * Número total de elementos en todas las páginas
     */
    private long totalElements;

    /**
     * Tamaño de cada página
     */
    private int pageSize;

    /**
     * Indica si existe una página siguiente
     */
    private boolean hasNext;

    /**
     * Indica si existe una página anterior
     */
    private boolean hasPrevious;

    /**
     * Constructor que convierte un objeto Page de Spring Data en una respuesta
     * paginada
     * 
     * @param page Objeto Page de Spring Data con los datos y metadatos de
     *             paginación
     */
    public PaginatedResponse(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }

    // Getters
    public List<T> getContent() {
        return content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }
}