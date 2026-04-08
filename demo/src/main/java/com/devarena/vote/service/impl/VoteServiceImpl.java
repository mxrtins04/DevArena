
public interface VoteService{
    public VoteResponseDto castVote(UUID votersId, UUID projectId);

    public List<VoteResponseDto> getVotesForProject(UUID projectId);

}