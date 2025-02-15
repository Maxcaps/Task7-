package state_management;

import org.example.state_management.ApplicationStateHolder;
import org.example.state_management.ApplicationStateManagerImpl;
import org.example.state_management.Statable;
import org.example.state_management.StateName;
import org.example.state_management.serialization.StateSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationStateManagerImplTest {

    private ApplicationStateManagerImpl stateManager;
    private StateSerializer mockSerializer;
    private ApplicationStateHolder mockStateHolder;
    private Statable<?> mockStatable; // Вынесенный мок объекта

    @BeforeEach
    void setUp() {
        mockSerializer = mock(StateSerializer.class); // Мокаем сериализатор
        mockStateHolder = mock(ApplicationStateHolder.class); // Мокаем хранилище состояний
        mockStatable = mock(Statable.class); // Создаем один раз и используем везде
        stateManager = new ApplicationStateManagerImpl("test/path", mockSerializer, mockStateHolder);
    }

    //  Тест сохранения состояния
    @Test
    void testSaveGlobalState() throws IOException {
        when(mockStatable.stateName()).thenReturn(StateName.AUTOCAD_RECORDER);
        when(mockStateHolder.getStateList()).thenReturn(Collections.singletonList(mockStatable));

        stateManager.saveGlobalState();

        verify(mockStateHolder, atLeastOnce()).getStateList();
        verify(mockSerializer, atLeastOnce()).serializeAndSave(any(Path.class), anyList());
    }

    //  Тест, если список пустой
    @Test
    void testSaveGlobalState_EmptyState() throws IOException {
        when(mockStateHolder.getStateList()).thenReturn(Collections.emptyList());

        stateManager.saveGlobalState();

        verify(mockStateHolder, atLeastOnce()).getStateList();
        verify(mockSerializer, never()).serializeAndSave(any(Path.class), anyList());
    }

    //  Тест загрузки состояния
//    @Test
//    void testLoadGlobalState() throws IOException {
//        List<Statable<?>> mockList = Collections.singletonList(mockStatable);
//        Map<StateName, List<Statable<?>>> mockLoadedStates = new HashMap<>();
//        mockLoadedStates.put(StateName.AUTOCAD_RECORDER, mockList);
//
//        when(mockSerializer.downloadAndDeserialize(any(Path.class), any())).thenReturn(mockList);
//        doReturn(mockLoadedStates).when(stateManager).download();
//
//        stateManager.loadGlobalState();
//
//        verify(mockStateHolder, times(1)).clear();
//
//        ArgumentCaptor<List<Statable<?>>> captor = ArgumentCaptor.forClass(List.class);
//        verify(mockStateHolder, times(1)).setStateList(captor.capture());
//
//        assertEquals(mockList, captor.getValue());
//    }

    //  Проверка обработки исключений при сохранении
    @Test
    void testSaveGlobalState_IOExceptionHandled() throws IOException {
        when(mockStatable.stateName()).thenReturn(StateName.AUTOCAD_RECORDER);
        when(mockStateHolder.getStateList()).thenReturn(Collections.singletonList(mockStatable));

        doThrow(new IOException("Ошибка записи")).when(mockSerializer).serializeAndSave(any(Path.class), anyList());

        assertDoesNotThrow(() -> stateManager.saveGlobalState());

        verify(mockSerializer, times(1)).serializeAndSave(any(Path.class), anyList());
    }

    //  Проверка обработки исключений при загрузке
//    @Test
//    void testLoadGlobalState_IOExceptionHandled() throws IOException {
//        doThrow(new IOException("Ошибка загрузки")).when(mockSerializer).downloadAndDeserialize(any(Path.class), any());
//
//        assertDoesNotThrow(() -> stateManager.loadGlobalState());
//
//        verify(mockSerializer, times(1)).downloadAndDeserialize(any(Path.class), any());
//    }
}

