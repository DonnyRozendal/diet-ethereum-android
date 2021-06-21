// SPDX-License-Identifier: UNLICENSED
pragma solidity 0.8.5;

contract Diet {
    
    event UserCreated(string email);
    event WeightEntryAdded(WeightEntry weightEntry);
    
    struct User {
        string email;
        string firstName;
        string lastName;
        string startWeight;
        string goalWeight;
        WeightEntry[] weightEntries;
    }
    
    struct WeightEntry {
        uint grams;
        uint timestamp;
    }
    
    mapping (address => User) ownerToUser;
    
    function getUserEmail() public view returns (string memory email) {
        return ownerToUser[msg.sender].email;
    }
    
    function getUserStartWeight() public view returns (string memory startWeight) {
        return ownerToUser[msg.sender].startWeight;
    }
    
    function getUserGoalWeight() public view returns (string memory goalWeight) {
        return ownerToUser[msg.sender].goalWeight;
    }
    
    function createUser(
        string memory _email,
        string memory _firstName,
        string memory _lastName,
        string memory _startWeight,
        string memory _goalWeight
    ) public {
        User storage user = ownerToUser[msg.sender];
        user.email = _email;
        user.firstName = _firstName;
        user.lastName = _lastName;
        user.startWeight = _startWeight;
        user.goalWeight = _goalWeight;

        emit UserCreated(user.email);
    }
    
    function getWeightEntries() public view returns (WeightEntry[] memory weightEntries) {
        return ownerToUser[msg.sender].weightEntries;
    }
    
    function addWeightEntry(
        uint _grams,
        uint _timestamp
    ) public {
        WeightEntry memory weightEntry = WeightEntry(_grams, _timestamp);
        ownerToUser[msg.sender].weightEntries.push(weightEntry);
        
        emit WeightEntryAdded(weightEntry);
    }
    
    function clearWeightEntries() public {
        delete ownerToUser[msg.sender].weightEntries;
    }

}