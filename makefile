run: compile
	cd bin && java controller.Logistics
	
compile:
	find -name "*.java" > temp_paths.txt
	javac -d "bin" @temp_paths.txt
	rm -f temp_paths.txt
	
clean:
	rm -r bin
