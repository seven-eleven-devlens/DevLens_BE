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

@Tag(name = "Administrator Project Management API", description = "API for administrator to create, read, update and delete projects")
@RequestMapping("/api/admin/project")
public interface AdminProjectDocs {

    @PostMapping("")
    @Operation(
            summary = "Post new project",
            description = "Post new project",
            responses =
                    {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Successfully created the new project",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PostProject.Response.class)
                                    )
                            )
                    }
    )
    ResponseEntity<APIResponse<PostProject.Response>> newProject(@RequestBody PostProject.Request request);

    @GetMapping("/{id}")
    @Operation(
            summary = "Get project detail",
            description = "Get project detail by project id",
            responses =
                    {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Successfully returned the existing project",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = GetProject.Response.class)
                                    )
                            )
                    },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Id of existing project",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetProject.Response>> readProject(@PathVariable Long id);

    @GetMapping("")
    @Operation(
            summary = "Get list of project page",
            description = "Get project list by paginated response",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully returned list of project",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProject.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number of project list",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetProject.Response>>> getListOfProjects(@RequestParam(value = "page") Integer page);

    @GetMapping("/histories")
    @Operation(
            summary = "Get Histories of projects",
            description = "Get histories of projects' creation and update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully returned list of projects' histories",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProject.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number of projects' histories list",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<ReadProjectHistory.Response>>> getListOfProjectHistory(@RequestParam(value = "page") Integer page);

    @GetMapping("/histories/{id}")
    @Operation(
            summary = "Get project history",
            description = "Retrieve the history of a specific project by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the project history",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReadProjectHistory.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the project history to retrieve",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<ReadProjectHistory.Response>> getProjectHistory(@PathVariable Long id);

    @GetMapping("/histories/search")
    @Operation(
            summary = "Search project histories",
            description = "Search project histories by project name",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully searched project histories",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReadProjectHistory.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "searchTerm",
                            description = "Search term for project history",
                            required = true,
                            example = "ai"
                    ),
                    @Parameter(
                            name = "page",
                            description = "Page number for pagination",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<ReadProjectHistory.Response>>> searchHistories(
            @RequestParam String searchTerm,
            @RequestParam Integer page
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Update project",
            description = "Update project",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated project",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PutProject.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Project's number",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PutProject.Response>> updateProject(@PathVariable Long id, @RequestBody PutProject.Request request);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a project",
            description = "Delete a project by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully deleted the project"
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the project to be deleted",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<Void>> deleteProject(@PathVariable Long id);
}
