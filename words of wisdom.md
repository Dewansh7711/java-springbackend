in quiz service we used Optional<> and .get() both works same but the difference i s Optional is better
when we are making a software so its always good to go with it instead of .get() but ur choice
now why we need it we need it for data where it might exist or not we dont know

i wanna scream real hard right now\

anyways back to the topic

we created the response file to use the response data so in model we have that response model now we have to use it in quiz service as list of responses to treat it

now lets talk about questionwrapper we created this so that we wont send the correct answers in the api call and just match it using the result api so we dont provide the right answer to the user
