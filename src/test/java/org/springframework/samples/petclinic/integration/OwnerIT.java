package org.springframework.samples.petclinic.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.springframework.samples.petclinic.web.OwnerController.SHOW_OWNER_BY_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OwnerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldFillOwnerInModelAndReturnOwnerDetailsWhenDisplayingSpecificId() throws Exception {
        mockMvc.perform(get(SHOW_OWNER_BY_ID, 10))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("owner"))
            .andExpect(model().attribute("owner", allOf(hasProperty("id", equalTo(10)),
                                                        hasProperty("lastName", equalToIgnoringCase("Estaban")))))
            .andExpect(view().name("owners/ownerDetails"));
    }

    @Test
    public void shouldFill10OwnersInModelAndReturnOwnersListNameWhenSearchingForEmptyName() throws Exception {
        mockMvc.perform(get("/owners.html").param("lastName", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("selections"))
            .andExpect(model().attribute("selections", hasSize(10)))
            .andExpect(view().name("owners/ownersList"));
    }

    @Test
    public void shouldFill2OwnersInModelAndReturnOwnersListVieWhenSearchingForNameEs() throws Exception {
        mockMvc.perform(get("/owners.html").param("lastName", "es"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("selections"))
            .andExpect(model().attribute("selections", hasSize(2)))
            .andExpect(view().name("owners/ownersList"));
    }
}
