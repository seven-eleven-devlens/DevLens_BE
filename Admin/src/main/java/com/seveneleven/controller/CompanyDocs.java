package com.seveneleven.controller;

import com.seveneleven.dto.*;
import com.seveneleven.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Administrator Company Management API", description = "API for administrators to manage companies")
@RequestMapping("/api/admin/companies")
public interface CompanyDocs {

    @PostMapping("")
    @Operation(
            summary = "Create a new company",
            description = "Create a new company and store it in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully created the company",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostCompany.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PostCompany.Response>> createCompany(@RequestBody PostCompany.Request companyRequest);

    @GetMapping("/{id}")
    @Operation(
            summary = "Get company details",
            description = "Retrieve detailed information about a specific company",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved company details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetCompanyDetail.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the company",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "page",
                            description = "Page number for projects",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetCompanyDetail.Response>> readCompany(
            @PathVariable Long id,
            @RequestParam(value = "page") Integer page
    );

    @GetMapping("")
    @Operation(
            summary = "Get list of companies",
            description = "Retrieve a paginated list of companies",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of companies",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetCompanies.Response>>> readCompanyList(@RequestParam(value = "page") Integer page);

    @GetMapping("/search")
    @Operation(
            summary = "Search companies by name",
            description = "Search for companies by name with pagination",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully searched companies",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "name",
                            description = "Name of the company to search for",
                            required = true,
                            example = "CompanyName"
                    ),
                    @Parameter(
                            name = "page",
                            description = "Page number for pagination",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetCompanies.Response>>> searchCompaniesByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page") Integer page
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Update company details",
            description = "Update the details of a specific company",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated company details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PutCompany.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the company to update",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PutCompany.Response>> updateCompany(
            @PathVariable Long id,
            @RequestBody PutCompany.Request request
    );

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete company",
            description = "Delete a company by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully deleted the company"
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the company to delete",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<Object>> deleteCompany(@PathVariable Long id);

    @GetMapping("/all")
    @Operation(
            summary = "Get all companies",
            description = "Retrieve a list of all companies",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved all companies",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<List<GetAllCompanies>>> readAllCompany();
}
