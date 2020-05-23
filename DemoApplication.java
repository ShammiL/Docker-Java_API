package com.example.demo;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.InvocationBuilder;
import com.github.dockerjava.core.command.PushImageResultCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) throws InterruptedException {

		//DefaultClientConfig contains details of the docker host we are connecting to
		DefaultDockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().
				withDockerHost("tcp://0.0.0.0:1111").build();
		//When build() is called, it creates a instance of the config class, which is needed when calling any docker task

		//create an instance of docker client, which will call methods to perform docker tasks
		DockerClient client = DockerClientBuilder.getInstance(clientConfig).build();
		//DockerClientBuilder is a factory to build docker client
		//client will communicate with the engine at the given address

		//List the containers
		List<Container> containers = client.listContainersCmd().withShowAll(true).exec();
		//ListContainerCmd by default returns the currently running containers list
		//withShowAll will return all containers
		//exec() will execute the whole thing

		//List images
		List<Image> images = client.listImagesCmd().withShowAll(true).exec();


	/*
		Iterator<Container> it = containers.iterator();

		while(it.hasNext()){
			Container container = it.next();
			System.out.println(container.getImage() + " " + container.getStatus());
		}

	 */

		Iterator<Image> itt = images.iterator();

		//while(itt.hasNext()){
		//	Image image = itt.next();
		//	System.out.println(image.getId());
		//}
		//SpringApplication.run(DemoApplication.class, args);

		//start a container
		//client.startContainerCmd("2f7f2b622bd5").exec();

		//Stop Container
		//client.stopContainerCmd("2f7f2b622bd5").exec();

		//kill Container
		//client.killContainerCmd("24ae31cf9f1a").exec();

		//remove container
		//client.removeContainerCmd("24ae31cf9f1a").exec();

		//inspect Container
		//InspectContainerResponse container = client.inspectContainerCmd(containers.get(0).getId()).exec();


		//create image snapshot of a container
		//String snapshotId = client.commitCmd(containers.get(0).getId()).exec();
		//System.out.println(snapshotId);

		//build an image from dockerfile
		//String imageId = client.buildImageCmd()
		//		.withDockerfile(new File("/home/shammi/Documents/helloWorld/Dockerfile"))
		//		.exec(new BuildImageResultCallback())
		//		.awaitImageId();
		//f2d2c9f33510 image was built from this last

		//System.out.println(imageId);

		/*
		//tag an image
		String imageId = "e59dba03edd1";
		String repository = "19980107/new_repo1";
		String tag = "test";

		client.tagImageCmd(imageId, repository, tag).exec();

		//push image
		//(I pushed two images, both were snapshots created from above code for creating snapshots)
		client.pushImageCmd("19980107/new_repo1")
				.withTag("test")
				.exec(new PushImageResultCallback())
				.awaitCompletion(240, TimeUnit.SECONDS);
		 */

		//remove image
		//client.removeImageCmd("9d9bc359de2a").exec();

		/*
		//pull image
		client.pullImageCmd("19980107/new_repo1")
				.withTag("mysql")
				.exec(new PullImageResultCallback())
				.awaitCompletion(30, TimeUnit.SECONDS);

		 */

		//search in registry
		/*
		List<SearchItem> items = client.searchImagesCmd("java").exec();

		Iterator<SearchItem> its = items.iterator();

		while(its.hasNext()){
			SearchItem sitem = its.next();
			System.out.println(sitem.getName() + " : " + sitem.getDescription());
		}
		
		 */



		InvocationBuilder.AsyncResultCallback<Statistics> callback = new InvocationBuilder.AsyncResultCallback<>();
		client.statsCmd("2f7f2b622bd5").exec(callback);
		Statistics stats = null;
		try {
			stats = callback.awaitResult();
			callback.close();
		} catch (RuntimeException | IOException e) {
			// you may want to throw an exception here
		}
		System.out.println("CPU Usage: " + stats.getCpuStats().toString()+ "/n");
		System.out.println("Memory Usage: " + stats.getMemoryStats().toString());
		



	}

}
