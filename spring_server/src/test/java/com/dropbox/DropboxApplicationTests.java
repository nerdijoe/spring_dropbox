package com.dropbox;

import com.dropbox.model.*;
import com.dropbox.service.FolderService;
import com.dropbox.service.UserService;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DropboxApplicationTests {
//	@Mock
//	private TestEntityManager entityManager;

	@Autowired
	private UserService userService;

	@Autowired
	private FolderService folderService;

	@Test
	public void UserCreate() {
		Users dude = new Users("dude", "the", "dude@haha.com", "haha");
//		entityManager.persist(dude);
//		entityManager.flush();
//
//		Users found = userService.findByEmail(dude.getEmail());
//
        assertThat(dude.getEmail()).isEqualTo("dude@haha.com");
        assertThat(dude.getFirstname()).isEqualTo("dude");
        assertThat(dude.getLastname()).isEqualTo("the");
        assertThat(dude.getPassword()).isEqualTo("haha");

    }

    @Test
    public void UserUpdate() {
        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        dude.setEmail("blablabla@hehe.com");
        dude.setPassword("hehe");
        dude.setFirstname("blablabla");
        dude.setLastname("dor");

        assertThat(dude.getEmail()).isEqualTo("blablabla@hehe.com");
        assertThat(dude.getFirstname()).isEqualTo("blablabla");
        assertThat(dude.getLastname()).isEqualTo("dor");
        assertThat(dude.getPassword()).isEqualTo("hehe");

    }

    @Test
    public void FileCreate() {

	    String name = "yeah.txt";
        String path = "./public/upload/";
        String full_path = path + name;
        String aws_s3_path = "aws";

        String type = "document";
        String size = "222";
        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        List<Users> emptyUsers = new ArrayList<Users>();


        Files file = new Files(name, path, full_path, aws_s3_path, type, size, dude);

        assertThat(file.getName()).isEqualTo(name);
        assertThat(file.getPath()).isEqualTo(path);
        assertThat(file.getFull_path()).isEqualTo(full_path);
        assertThat(file.getAws_s3_path()).isEqualTo(aws_s3_path);
        assertThat(file.getType()).isEqualTo(type);
        assertThat(file.getSize()).isEqualTo(size);

        assertThat(file.getUser()).isEqualTo(dude);
        assertThat(file.getUsers()).isEqualTo(emptyUsers);

        assertThat(file.getIs_deleted()).isEqualTo(false);
        assertThat(file.getIs_starred()).isEqualTo(false);


    }

    @Test
    public void FileUpdate() {

        String name = "yeah.txt";
        String path = "./public/upload/";
        String full_path = path + name;
        String aws_s3_path = "aws";

        String type = "document";
        String size = "222";
        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        List<Users> emptyUsers = new ArrayList<Users>();


        Files file = new Files(name, path, full_path, aws_s3_path, type, size, dude);

        String name2 = "yeehaa.txt";
        String path2 = "./public/upload/";
        String full_path2 = path + name;
        String aws_s3_path2 = "aws.s3";

        String type2 = "zip";
        String size2 = "333";
        Users dude2 = new Users("rodg", "the", "rodg@haha.com", "hehe");

        file.setName(name2);
        file.setPath(path2);
        file.setFull_path(full_path2);
        file.setAws_s3_path(aws_s3_path2);
        file.setType(type2);
        file.setSize(size2);

        file.setUser(dude2);


        assertThat(file.getName()).isEqualTo(name2);
        assertThat(file.getPath()).isEqualTo(path2);
        assertThat(file.getFull_path()).isEqualTo(full_path2);
        assertThat(file.getAws_s3_path()).isEqualTo(aws_s3_path2);
        assertThat(file.getType()).isEqualTo(type2);
        assertThat(file.getSize()).isEqualTo(size2);

        assertThat(file.getUser()).isEqualTo(dude2);
        assertThat(file.getUsers()).isEqualTo(emptyUsers);

        assertThat(file.getIs_deleted()).isEqualTo(false);
        assertThat(file.getIs_starred()).isEqualTo(false);
    }

    @Test
    public void FolderCreate() {

        String name = "yeah";
        String path = "./public/upload/";
        String full_path = path + name;

        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        List<Users> emptyUsers = new ArrayList<Users>();


        Folders folder = new Folders(name, path, full_path, dude);

        assertThat(folder.getName()).isEqualTo(name);
        assertThat(folder.getPath()).isEqualTo(path);
        assertThat(folder.getFull_path()).isEqualTo(full_path);

        assertThat(folder.getUser()).isEqualTo(dude);
        assertThat(folder.getUsers()).isEqualTo(emptyUsers);

        assertThat(folder.getIs_deleted()).isEqualTo(false);
        assertThat(folder.getIs_starred()).isEqualTo(false);

    }

    @Test
    public void FolderUpdate() {

        String name = "yeah";
        String path = "./public/upload/";
        String full_path = path + name;

        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        List<Users> emptyUsers = new ArrayList<Users>();

        Folders folder = new Folders(name, path, full_path, dude);

        String name2 = "yeehaa.txt";
        String path2 = "./public/upload/";
        String full_path2 = path + name;
        Users dude2 = new Users("rodg", "the", "rodg@haha.com", "hehe");

        folder.setName(name2);
        folder.setPath(path2);
        folder.setFull_path(full_path2);

        folder.setUser(dude2);

        assertThat(folder.getName()).isEqualTo(name2);
        assertThat(folder.getPath()).isEqualTo(path2);
        assertThat(folder.getFull_path()).isEqualTo(full_path2);

        assertThat(folder.getUser()).isEqualTo(dude2);
        assertThat(folder.getUsers()).isEqualTo(emptyUsers);

        assertThat(folder.getIs_deleted()).isEqualTo(false);
        assertThat(folder.getIs_starred()).isEqualTo(false);
    }

    @Test
    public void AboutCreate() {

        String overview = "this is my overview";
        String work = "working";
        String education = "school";
        String contact_info = "33-33-3-3-3";
        String life_events = "winning";


        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        Abouts about = new Abouts(overview, work, education, contact_info, life_events, dude);

        assertThat(about.getOverview()).isEqualTo(overview);
        assertThat(about.getWork()).isEqualTo(work);
        assertThat(about.getEducation()).isEqualTo(education);
        assertThat(about.getContact_info()).isEqualTo(contact_info);
        assertThat(about.getLife_events()).isEqualTo(life_events);

        assertThat(about.getUser()).isEqualTo(dude);
    }

    @Test
    public void AboutUpdate() {

        String overview = "this is my overview";
        String work = "working";
        String education = "school";
        String contact_info = "33-33-3-3-3";
        String life_events = "winning";

        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        Abouts about = new Abouts(overview, work, education, contact_info, life_events, dude);

        String overview2 = "this is my overview again";
        String work2 = "working now";
        String education2 = "school no more";
        String contact_info2 = "44404404040";
        String life_events2 = "losing";

        Users dude2 = new Users("harry", "potter", "potter@haha.com", "wand");

        about.setOverview(overview2);
        about.setWork(work2);
        about.setEducation(education2);
        about.setContact_info(contact_info2);
        about.setLife_events(life_events2);
        about.setUser(dude2);

        assertThat(about.getOverview()).isEqualTo(overview2);
        assertThat(about.getWork()).isEqualTo(work2);
        assertThat(about.getEducation()).isEqualTo(education2);
        assertThat(about.getContact_info()).isEqualTo(contact_info2);
        assertThat(about.getLife_events()).isEqualTo(life_events2);

        assertThat(about.getUser()).isEqualTo(dude2);
    }

    @Test
    public void InterestCreate() {

        String music = "classic";
        String shows = "mr.robot";
        String sports = "football";
        String fav_teams = "nygiants";

        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        Interests interest = new Interests(music, shows, sports, fav_teams, dude);

        assertThat(interest.getMusic()).isEqualTo(music);
        assertThat(interest.getShows()).isEqualTo(shows);
        assertThat(interest.getSports()).isEqualTo(sports);
        assertThat(interest.getFav_teams()).isEqualTo(fav_teams);

        assertThat(interest.getUser()).isEqualTo(dude);
    }

    @Test
    public void InterestUpdate() {

        String music = "classic";
        String shows = "mr.robot";
        String sports = "football";
        String fav_teams = "nygiants";

        Users dude = new Users("dude", "the", "dude@haha.com", "haha");

        Interests interest = new Interests(music, shows, sports, fav_teams, dude);

        String music2 = "rock";
        String shows2 = "lost";
        String sports2 = "football and rugby";
        String fav_teams2 = "seahawks";

        Users dude2 = new Users("big", "boss", "big@haha.com", "bossss");

        interest.setMusic(music2);
        interest.setShows(shows2);
        interest.setSports(sports2);
        interest.setFav_teams(fav_teams2);
        interest.setUser(dude2);

        assertThat(interest.getMusic()).isEqualTo(music2);
        assertThat(interest.getShows()).isEqualTo(shows2);
        assertThat(interest.getSports()).isEqualTo(sports2);
        assertThat(interest.getFav_teams()).isEqualTo(fav_teams2);

        assertThat(interest.getUser()).isEqualTo(dude2);
    }



}
