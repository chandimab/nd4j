package org.nd4j.linalg.workspace;

import lombok.NonNull;
import org.nd4j.linalg.api.memory.MemoryWorkspace;
import org.nd4j.linalg.api.memory.conf.WorkspaceConfiguration;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 *
 * WorkspaceMgr is an interface for managing a set of workspaces, for a set of array types (where the array types
 * are specified by an enumeration).
 * Note that multiple array types may be stored in the one underlying 
 *
 * @param <T> Enumeration type to specify the type of array. For example, in DL4J the type values include things
 *           like inputs, activations, working memory etc.
 *
 * @author Alex Black
 */
public interface WorkspaceMgr<T extends Enum<T>> {

    /**
     *
     * @param workspace
     * @param configuration
     */
    void setConfiguration(T workspace, WorkspaceConfiguration configuration);

    WorkspaceConfiguration getConfiguration(T arrayType);

    void setScopedOutFor(T arrayType);

    boolean isScopedOut(T arrayType);

    MemoryWorkspace notifyScopeEntered(T arrayType);

    AutoCloseable notifyScopeEntered(T... arrayTypes);

    MemoryWorkspace notifyScopeBorrowed(T workspace);

    void setWorkspaceName(T arrayType, String wsName);

    String getWorkspaceName(T arrayType);

    void setWorkspace(T arrayType, String wsName, WorkspaceConfiguration configuration);

    boolean isWorkspaceOpen(T arrayType);

    void assertNotOpen(T arrayType, String msg);

    void assertCurrentWorkspace(T arrayType, String msg);

    /**
     * If the array is not attached (not defined in a workspace) - array is returned unmodified
     *
     * @param toWorkspace
     * @param array
     * @return
     */
    INDArray leverageTo(T toWorkspace, INDArray array);

    /**
     * Validate that the specified array type is actually in the workspace it's supposed to be in
     *
     * @param arrayType        Array type of the array
     * @param array            Array to check
     * @param migrateIfInvalid if true: migrate. If false: exception
     * @param exceptionIfDetached If true: if the workspace is detached, but is expected to be in a workspace: should an
     *                            exception be thrown?
     * @return
     */
    INDArray validateArrayLocation(T arrayType, INDArray array, boolean migrateIfInvalid, boolean exceptionIfDetached);

    INDArray create(T workspace, int... shape);

    INDArray create(T workspace, int[] shape, char ordering);

    INDArray createUninitialized(T arrayType, int... shape);

    INDArray createUninitialized(T arrayType, int[] shape, char order);

    INDArray dup(T arrayType, INDArray toDup, char order);

    INDArray dup(@NonNull T arrayType, @NonNull INDArray toDup);


}